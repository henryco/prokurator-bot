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
import java.util.Random;

@Component @Slf4j
public class NewDayNewMeCommand extends AtencjoBotCommandListener {

	private static final String[] set = {
			"The", "A", "Un", "In", "Rape", "Epic", "", "xXx", "Robbie", "TRIGGERED",
			"Barry", "Dat", "Over", "Under", "Inside", "Lil'", "Big", "F", "Baka",
			"Pepe", "Legend", "Harambe", "MemeQueen", "Kops", "Rotten", "Bee", "Boi",
			"Watch", "Tale", "Shrek", "Donkey", "Master", "2137", "BENIS", "Hitler",
			"27", "23", "xXx", "420", "2016", "2017", "Benson", "2019", "2078", "DidnDoNothing",
			"Waddup", "ing", "Baiter", "NumberOne", "'d", "ddd", "XD", "xd", "Xd"
	};

	private static String rollName() {
		val count = new Random().nextInt(2) + 2;
		val str = new StringBuilder();
		for (int i = 0; i < count; i++) {
			str.append(set[new Random().nextInt(set.length)]);
		}
		return str.toString().substring(0, Math.min(str.length(), 32));
	}

	@Override
	public void onCommand(MessageReceivedEvent event, GuildController controller) {
		val list = new ArrayList<User>(event.getMessage().getMentionedUsers());
		if (list.isEmpty()) list.add(event.getAuthor());
		for (val user : list) {
			log.debug("USER TO CHANGE: {}", user);
			controller.setNickname(event.getGuild().getMember(user), rollName()).queue();
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