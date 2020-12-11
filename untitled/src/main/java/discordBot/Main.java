package discordBot;

import discordCommands.Commands;
import discordCommands.Verfier;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main extends ListenerAdapter {
    Filter f = new Filter();
    final char PREFIX = '!';
    List<Member> list = new ArrayList<>();
    boolean initFlag = false;
    List <MessageChannel> textChannel = new ArrayList<>();
    League lol = new League();
    Map<String, Commands> map = new HashMap<>();

    public static void main(String[] args) throws LoginException{
        String token = "NzU1MDUxMTE1NjY1NDI0NTE2.X19qJg.xOEqhxfe8yH_Js12-dPRip3JlSw";
        JDA jda = JDABuilder.createDefault(token).addEventListeners(new Main()).build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if ((PREFIX + "init").equalsIgnoreCase(args[0])&&!initFlag) {
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("start init").queue();
            initFlag=init(event);
        }else if(args.length>1&&map.get(args[0])!=null) {
            map.get(args[0]).execute(event,args[1]);
        }
                /*case (PREFIX + "verifywithid"):
                if (args.length < 2) {
                    event.getChannel().sendMessage("I need a ID to verify!").queue();
                } else {
                    verifyWithIDUser(args[1],event);
                }
                break;
            case (PREFIX + "verifywithname"):
                if (args.length < 2) {
                    event.getChannel().sendMessage("Username is required").queue();
                } else {
                    verifyWithUsername(args[1],event);
                }
                break;
            case (PREFIX + "addinsults"):
                if (args.length > 2) f.addInsults(args[1]);
                break;
            case (PREFIX + "dontmoveon"):
                printString("true");
                event.getChannel().sendMessage(event.getAuthor() +" can not be moved out of Ranked").queue();
                list.add(event.getMember());
                break;
            case (PREFIX + "dontmoveoff"):
                printString("false");
                for(int i =0;i<list.size();i++){
                    if(list.get(i)==event.getMember()){
                        list.remove(i);
                        event.getChannel().sendMessage(event.getAuthor() +" can be moved out of Ranked").queue();
                    }
                }
                break;
            case (PREFIX + "summoner"):
                if(args[1]!=null){
                    try {
                        event.getChannel().sendMessage(lol.getSummerInfo(args[1])).queue();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case (PREFIX + "summonermatchhistory"):
                try {
                    lol.getMatchHistory(args[1]);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case(PREFIX + "get"):
                Guild g = event.getGuild();
                System.out.println(event.getGuild().getMembers());

                break;*/


    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        for(Member m:list) {
            if(m==event.getMember()) {
                event.getGuild().moveVoiceMember(event.getMember(), event.getGuild().getVoiceChannelsByName("Ranked", true).get(0)).complete();
            }
        }
    }

    @Override
    public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent event) {
        if (f.findInsult(event.getNewNickname())) {
            event.getMember().modifyNickname("rename").complete();
        }
        System.out.println("test");
    }

    private void verifyWithIDUser(String userID, MessageReceivedEvent event){
        long tempLong = Long.parseLong(userID);
        for (Role r : event.getGuild().getRoles()) {
            if (r.getName().equals("member") && (event.getGuild().getMemberById(tempLong) != null)) {
                System.out.println("verified!");
                event.getGuild().addRoleToMember(tempLong, r).complete();
                String text = event.getGuild().getMemberById(tempLong).getNickname() + " " + "is verified!";
                event.getChannel().sendMessage(text).queue();
            }
        }
    }

    private void verifyWithUsername(String userName, MessageReceivedEvent event){
        for (Role r : event.getGuild().getRoles()) {
            if (r.getName().equals("member")) {
                if (!(event.getGuild().getMembersByName(userName, true).isEmpty())) {
                    Member m = event.getGuild().getMembersByName(userName, true).get(0);
                    event.getGuild().addRoleToMember(m.getId(), r).complete();
                    String text = event.getGuild().getMemberById(m.getId()).getNickname() + " " + "is verified!";
                    System.out.println("verified!");
                    event.getChannel().sendMessage(text).queue();
                }
            }
        }
    }

    @Override
    public void onReady(ReadyEvent e){

    }

    public boolean init(MessageReceivedEvent event){
        map.put("!verify", new Verfier());
        return true;
    }



}
