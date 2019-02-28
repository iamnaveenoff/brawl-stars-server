package daniillnull.javacr.server;

import daniillnull.javacr.game.Alliance;
import daniillnull.javacr.game.AllianceChatMessage;
import daniillnull.javacr.game.Player;
import daniillnull.javacr.messages.MessageOutputStream;
import daniillnull.javacr.messages.client.ChangeAlliance;
import daniillnull.javacr.messages.client.CreateAlliance;
import daniillnull.javacr.messages.client.PromoteAllianceMember;

public class Session implements Runnable
{
  public Thread curr;
  public daniillnull.javacr.messages.MessageInputStream is;
  public MessageOutputStream os;
  public daniillnull.javacr.encryption2v.Crypt cr;
  public Player player;
  
  public Session() {}
  
  public void run()
  {
    try
    {
      work();
    } catch (Exception e) {
      if ((!(e instanceof java.io.IOException)) && (!(e instanceof java.net.SocketException)) && (!(e instanceof java.io.EOFException))) {
        e.printStackTrace();
      }
      try
      {
        Main.sessions.remove(this);
        is.close();
        os.close();
        player.current = null;
      } catch (Exception localException1) {}
    }
  }
  
  public void work() throws Exception {
    daniillnull.javacr.messages.client.SessionReq rr = (daniillnull.javacr.messages.client.SessionReq)is.read();
    os.write(new daniillnull.javacr.messages.server.SessionOk());
    daniillnull.javacr.messages.client.Login login = (daniillnull.javacr.messages.client.Login)is.read();
    
    if ((!Main.fingerPrintHash.isEmpty()) && (!rhash.equals(Main.fingerPrintHash))) {
      os.write(new daniillnull.javacr.messages.server.UpdateResources());
      return;
    }
    
    player = Player.load(vid);
    player.current = this;
    
    os.write(new daniillnull.javacr.messages.server.LoginOk(player.id));
    os.write(new daniillnull.javacr.messages.server.OwnHomeData(player));
    
    if (player.kickedfrom != 0) {
      os.write(new daniillnull.javacr.messages.server.AllianceCommandOk(Alliance.load(player.kickedfrom), 141, true));
      player.kickedfrom = 0;
    }
    
    if (player.all != null) {
      os.write(new daniillnull.javacr.messages.server.AllianceMessageStream(player.all.messages));
    }
    
    while (!Thread.interrupted()) {
      daniillnull.javacr.messages.Packet pk = is.read();
      
      if ((pk instanceof daniillnull.javacr.messages.client.KeepAlive)) {
        os.write(new daniillnull.javacr.messages.server.KeepAliveOk());
      }
      
      if ((pk instanceof daniillnull.javacr.messages.client.GetProfileData)) {
        daniillnull.javacr.messages.client.GetProfileData m = (daniillnull.javacr.messages.client.GetProfileData)pk;
        
        os.write(new daniillnull.javacr.messages.server.ProfileData(Player.load(uid)));
      }
      
      if ((pk instanceof daniillnull.javacr.messages.client.GetAllianceData)) {
        daniillnull.javacr.messages.client.GetAllianceData m = (daniillnull.javacr.messages.client.GetAllianceData)pk;
        
        os.write(new daniillnull.javacr.messages.server.AllianceData(Alliance.load(cid)));
        
        if (player.all != null) {
          os.write(new daniillnull.javacr.messages.server.AllianceOnlinePlayersCount(player.all));
        }
      }
      
      if ((pk instanceof ChangeAlliance)) {
        ChangeAlliance m = (ChangeAlliance)pk;
        
        player.all.descr = descr;
        player.all.badge = badge;
        player.all.reqsc = reqsc;
        player.all.orig = orig;
        
        os.write(new daniillnull.javacr.messages.server.AllianceData(player.all));
      }
      
      if ((pk instanceof PromoteAllianceMember)) {
        PromoteAllianceMember m = (PromoteAllianceMember)pk;
        Player pl = Player.load(uid);
        
        if ((player.all == all) && ((player.role == 2) || (role != 2))) {
          int[] roles = { 1, 1, 4, 2, 3 };
          int old = role;
          
          role = role;
          player.all.addMessage(AllianceChatMessage.createEvent(roles[old] > roles[role] ? 6 : 5, pl, player.name));
          
          if (role == 2) {
            player.role = 4;
            player.all.addMessage(AllianceChatMessage.createEvent(6, player, player.name));
          }
        }
      }
      
      if ((pk instanceof daniillnull.javacr.messages.client.SendAllianceMessage)) {
        daniillnull.javacr.messages.client.SendAllianceMessage m = (daniillnull.javacr.messages.client.SendAllianceMessage)pk;
        
        AllianceChatMessage message = AllianceChatMessage.createText(message, player);
        player.all.addMessage(message);
      }
      
      if ((pk instanceof CreateAlliance)) {
        CreateAlliance m = (CreateAlliance)pk;
        
        player.all = Alliance.load(0);
        player.all.players.add(player);
        player.role = 2;
        
        player.all.name = name;
        player.all.descr = descr;
        player.all.badge = badge;
        player.all.reqsc = reqsc;
        player.all.orig = orig;
        
        os.write(new daniillnull.javacr.messages.server.AllianceCommandOk(player.all, 142, true));
      }
      
      if ((pk instanceof daniillnull.javacr.messages.client.JoinAlliance)) {
        daniillnull.javacr.messages.client.JoinAlliance m = (daniillnull.javacr.messages.client.JoinAlliance)pk;
        AllianceChatMessage message = AllianceChatMessage.createEvent(3, player, "");
        
        player.all = Alliance.load(cid);
        player.all.addMessage(message);
        player.all.players.add(player);
        player.role = 1;
        
        os.write(new daniillnull.javacr.messages.server.AllianceCommandOk(player.all, 142, false));
        os.write(new daniillnull.javacr.messages.server.AllianceMessageStream(player.all.messages));
      }
      
      if ((pk instanceof daniillnull.javacr.messages.client.LeaveAlliance)) {
        AllianceChatMessage message = AllianceChatMessage.createEvent(4, player, "");
        
        player.all.players.remove(player);
        player.all.addMessage(message);
        
        if ((player.role == 2) && (player.all.players.size() > 0)) {
          player.all.players.get(0)).role = 2;
        }
        
        os.write(new daniillnull.javacr.messages.server.AllianceCommandOk(player.all, 141, false));
        player.all = null;
      }
      
      if ((pk instanceof daniillnull.javacr.messages.client.GetJoinableAlliances)) {
        os.write(new daniillnull.javacr.messages.server.JoinableAlliances());
      }
      
      if ((pk instanceof daniillnull.javacr.messages.client.ExecuteCmd)) {
        daniillnull.javacr.messages.client.ExecuteCmd m = (daniillnull.javacr.messages.client.ExecuteCmd)pk;
        m.executeAll(this);
      }
      
      if ((pk instanceof daniillnull.javacr.messages.client.StartMission)) {
        os.write(new daniillnull.javacr.messages.server.SectorState(player, 0));
      }
      
      if ((pk instanceof daniillnull.javacr.messages.client.GetHomeData)) {
        os.write(new daniillnull.javacr.messages.server.OwnHomeData(player));
      }
      
      if ((pk instanceof daniillnull.javacr.messages.client.SetName)) {
        daniillnull.javacr.messages.client.SetName m = (daniillnull.javacr.messages.client.SetName)pk;
        
        player.name = name;
        os.write(new daniillnull.javacr.messages.server.ChangeNameOk(name));
      }
    }
  }
}
