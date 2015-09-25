/**
 * The controller package has several responsibilities:
 * <ul>
 *	<li>Receiving events from the user and sending them to the model in the appropriate format.</li>
 *	<li>Maintaining the game loop, which causes the model to update itself and makes the view repaint.</li>
 *	<li>Creating Client and Server threads which pass event and world information between each other in a network.</li>
 * </ul>
 *
 * The {@link Clock} class maintains the game loop. The {@link Client} and {@link Server} class handle the
 * client/server architecture. The Client sends information about user events when they happen to the Server,
 * and in return the Server regularly updates the Client with the state of the game world. In this way, the
 * client/server classes also act to receive events and send them to the model. When only running a single
 * player game, the {@link SinglePlayer} class fills the role of the client and server. It receives user events
 * when they happen and also passes them to the model at the same time.
 *
 * @author Sam Costigan
 */
package controller;