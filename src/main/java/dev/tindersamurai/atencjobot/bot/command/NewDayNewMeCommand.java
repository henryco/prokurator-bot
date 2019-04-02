package dev.tindersamurai.atencjobot.bot.command;

import dev.tindersamurai.atencjobot.bot.AtencjoBotCommandListener;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component @Slf4j
public class NewDayNewMeCommand extends AtencjoBotCommandListener {

	@Override
	public void onCommand(MessageReceivedEvent event) {

		// todo roll name
		log.debug("New Day Command");
	}

	@Override
	public String getCommandName() {
		return CommandsContractList.NEW_DAY;
	}
}