package dev.tindersamurai.atencjobot.bot.command;

import dev.tindersamurai.atencjobot.bot.AtencjoBotCommandListener;
import lombok.extern.slf4j.Slf4j;

import lombok.val;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.GuildController;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component @Slf4j
public class NewDayNewMeCommand extends AtencjoBotCommandListener {

	@Override
	public void onCommand(MessageReceivedEvent event, GuildController controller) {
		val list = new ArrayList<User>(event.getMessage().getMentionedUsers());
		if (list.isEmpty()) list.add(event.getAuthor());
		for (val user : list) {
			log.debug("USER TO CHANGE: {}", user);
			controller.setNickname(event.getGuild().getMember(user), "BINGO").queue();
		}
	}

	@Override
	protected Permission[] requiredUserPermissions() {
		return new Permission[] { Permission.NICKNAME_CHANGE };
	}

	@Override
	protected Permission[] requiredBotPermissions() {
		return new Permission[] { Permission.NICKNAME_CHANGE };
	}

	@Override
	public String commandName() {
		return CommandsContractList.NEW_DAY;
	}
}