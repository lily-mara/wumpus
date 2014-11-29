package edu.miamioh.cse283.htw;

public class Protocol {

	/**
	 * Handoff message.
	 * <p/>
	 * This message is sent by the CaveSystemServer to the Client.
	 * When received, the client shall immediately disconnect
	 * from the CaveSystemServer, and then reconnect to the CaveServer
	 * whose address and port number are given in the message.
	 * <p/>
	 * Format:
	 * HANDOFF <addr> <port>
	 * <p/>
	 * <addr> is a string that can be passed to InetAddress.getByName()
	 * <port> is an integer between 1025 and 65535.
	 */
	public static final String HANDOFF = "HANDOFF";

	/**
	 * Connect message.
	 * <p/>
	 * This message is sent by the Client to the CaveSystemServer to
	 * indicate which CaveServer the player has selected to play in.
	 * <p/>
	 * Upon reception of this message, the CaveSystemServer shall respond
	 * with a HANDOFF message.
	 * <p/>
	 * Format:
	 * CONNECT <server name>
	 * <p/>
	 * <server name> is a String that the CaveSystemServer uses to lookup
	 * the address and port number for the CaveServer that the client will
	 * be handed off to.
	 */
	public static final String CONNECT = "CONNECT";

	/**
	 * Notification message.
	 * <p/>
	 * This block message is sent to the client, and
	 * is used to communicate one-time messages.  The contents
	 * of this message shall be displayed to the user one time ONLY.
	 * <p/>
	 * This message type shall be used to send all of the following
	 * from the CaveSystemServer:
	 * - List of servers that the user can select from
	 * <p/>
	 * This message type shall be used to send all of the following
	 * from the CaveServer:
	 * - Notifications of player death elsewhere on the same CaveServer
	 * - Cause of death
	 * - Score in the event that a player climbs the ladder
	 * <p/>
	 * Format:
	 * BEGIN NOTIFICATION
	 * <line>
	 * ...
	 * END NOTIFICATION
	 * <p/>
	 * <line> is a String that should be displayed to the user once
	 * ... represent zero or more additional lines.
	 * <p/>
	 * Each line in the above format is to be followed by a newline character,
	 * i.e., each line is the result of separate println() command.
	 */
	public static final String BEGIN_NOTIFICATION = "BEGIN NOTIFICATION";
	public static final String END_NOTIFICATION = "END NOTIFICATION";

	/**
	 * Quit message.
	 * <p/>
	 * This message is sent from the Client to a CaveServer to indicate
	 * that the Client is quitting the game.  Upon reception of this message,
	 * the CaveServer shall clean release resources used by the Client.  No
	 * response to this message shall be sent.  After sending this message,
	 * the Client shall release any resources used to communicate with the
	 * CaveServer, and then exit.
	 * <p/>
	 * Format:
	 * QUIT
	 * <p/>
	 * (No parameters to this message are allowed.)
	 */
	public static final String QUIT = "QUIT";

	/**
	 * Died message.
	 * <p/>
	 * This message is sent from the CaveServer to the Client whenever
	 * the player dies or elects to climb the ladder.  Resources shall be
	 * released as with the QUIT message.
	 * <p/>
	 * Format:
	 * DIED
	 * <p/>
	 * While no message is included pertaining to the cause of death,
	 * the CaveServer may send additional details (e.g., score, cause of
	 * death, etc.) as notification messages prior to sending this message.
	 */
	public static final String DIED = "DIED";

	/**
	 * Senses message.
	 * <p/>
	 * This block message is sent from the CaveServer to the Client, and
	 * is used to communicate what the player senses to the Client.  The
	 * contents of this message shall be displayed to the player following
	 * every action.
	 * <p/>
	 * Format:
	 * BEGIN SENSES
	 * <line>
	 * ...
	 * END SENSES
	 * <p/>
	 * <line> is a String that should be displayed to the user.
	 * ... represent zero or more additional lines.
	 * <p/>
	 * Each line in the above format is to be followed by a newline character,
	 * i.e., each line is the result of separate println() command.
	 */
	public static final String BEGIN_SENSES = "BEGIN SENSES";
	public static final String END_SENSES = "END SENSES";

	/**
	 * Registration message.
	 * <p/>
	 * This message is sent from the CaveServer to the CaveSystemServer immediately
	 * following a CaveServer's connection to the CaveSystemServer.  It is used
	 * to communicate the address, port#, and human-readable name by which
	 * a Client can play the game with that CaveServer.
	 * <p/>
	 * Format:
	 * REGISTER <addr> <port> <name>
	 * <p/>
	 * <addr> is a string that can be passed to InetAddress.getByName()
	 * <port> is an integer between 1025 and 65535.
	 * <name> is a String that will be displayed to the user and that can
	 * by used in the CONNECT message to select a CaveServer that the Client
	 * will play.
	 */
	public static final String REGISTER = "REGISTER";

	/**
	 * Action message.
	 * <p/>
	 * This message is sent from the Client to the CaveServer when the
	 * player performs an action.  Upon receiving this message, the
	 * CaveServer shall perform that action on the player's behalf, and
	 * respond with a SENSES message.  In the event that the action message
	 * is invalid, the CaveServer shall leave the player's state unchanged,
	 * and respond with a SENSES message that includes a useful error message.
	 * <p/>
	 * Format:
	 * ACTION <number> <optional room id>
	 * <p/>
	 * <number> is one of the valid player actions:
	 * 1: move
	 * 2: shoot
	 * 3: pickup
	 * 4: climb
	 * <optional room id> is used by 1:move and 2:shoot to indicate into which
	 * room a player (arrow) is to be moved (shot).
	 */
	public static final String MOVE_ACTION = "ACTION 1";
	public static final String SHOOT_ACTION = "ACTION 2";
	public static final String PICKUP_ACTION = "ACTION 3";
	public static final String CLIMB_ACTION = "ACTION 4";
}
