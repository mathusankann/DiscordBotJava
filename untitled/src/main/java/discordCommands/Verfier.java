package discordCommands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Verfier implements Commands {
    @Override
    public String execute(MessageReceivedEvent event, String ID) {
        for (Role r : event.getGuild().getRoles()) {
            if (r.getName().equals("member")) {
                if (!(event.getGuild().getMembersByName(ID, true).isEmpty())) {
                    Member m = event.getGuild().getMembersByName(ID, true).get(0);
                    event.getGuild().addRoleToMember(m.getId(), r).complete();
                    String text = event.getGuild().getMemberById(m.getId()).getNickname() + " " + "is verified!";
                    System.out.println("verified!");
                    event.getChannel().sendMessage(text).queue();
                }
            }
        }
        return null;
    }

    @Override
    public String execute(MessageReceivedEvent event, long ID) {
        return null;
    }
}
