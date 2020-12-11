package discordCommands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Commands {

    public String execute(MessageReceivedEvent event,String ID);
    public String execute(MessageReceivedEvent event,long ID);

}
