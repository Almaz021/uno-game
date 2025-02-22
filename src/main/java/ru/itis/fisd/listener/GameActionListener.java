package ru.itis.fisd.listener;

import javafx.scene.control.Button;
import lombok.Getter;
import ru.itis.fisd.app.Game;
import ru.itis.fisd.app.GameLogic;
import ru.itis.fisd.app.GameState;
import ru.itis.fisd.client.Client;
import ru.itis.fisd.client.gui.controller.GameFXController;
import ru.itis.fisd.entity.Card;
import ru.itis.fisd.entity.CardColor;
import ru.itis.fisd.entity.Player;
import ru.itis.fisd.protocol.Protocol;
import ru.itis.fisd.protocol.ProtocolType;

@Getter
public class GameActionListener {

    @Getter
    public static GameActionListener instance;

    private final GameFXController gameFXController;

    public GameActionListener(GameFXController gameFXController) {
        instance = this;
        this.gameFXController = gameFXController;
    }


    public void handleButton(Button button, Player player) {
        Client client = Game.client;
        if (client.getPlayer() == null) {
            client.setPlayer(player);
        }
        System.out.println(client + " " + client.getSocket());
        System.out.println(button.getText() + " " + button.getBackground().getFills().getFirst().getFill());
        System.out.println("ORDER: " + client.getOrder());
        System.out.println("STATUS " + GameState.isStart + " " + GameState.order);
        GameLogic logic = new GameLogic();
        CardColor cardColor = switch (button.getBackground().getFills().getFirst().getFill().toString()) {
            case "0xff0000ff" -> CardColor.RED;
            case "0x0000ffff" -> CardColor.BLUE;
            case "0xffff00ff" -> CardColor.YELLOW;
            case "0x008000ff" -> CardColor.GREEN;
            default ->
                    throw new IllegalStateException("Unexpected value: " + button.getBackground().getFills().getFirst().getFill().toString());
        };
        Card card = new Card(Integer.parseInt(button.getText()), cardColor);
        if (!GameState.isStart) {
            System.out.println("CHECKING" + logic.handleMove(card, GameState.currentCard));
            if (client.getOrder() == GameState.order && logic.handleMove(card, GameState.currentCard)) {
                gameFXController.setDeckCard(button.getText(),
                        button.getBackground().getFills().getFirst().getFill());

                client.sendMessage(new Protocol(ProtocolType.GAME, "move:" + client.getOrder() + ":" + button.getText()));
                client.sendMessage(new Protocol(ProtocolType.GAME, "card:" + button.getText() + "&" + button.getBackground().getFills().getFirst().getFill()));
            }
        } else {
            System.out.println("CHECKING" + logic.handleMove(card, GameState.currentCard));
            if (client.getOrder() == GameState.order && logic.handleMove(card, GameState.currentCard)) {
                gameFXController.setDeckCard(button.getText(),
                        button.getBackground().getFills().getFirst().getFill());

                client.sendMessage(new Protocol(ProtocolType.GAME, "move:" + client.getOrder() + ":" + button.getText()));
                client.sendMessage(new Protocol(ProtocolType.GAME, "card:" + button.getText() + "&" + button.getBackground().getFills().getFirst().getFill()));
            }
        }
    }

    public void handleDeck() {
        Client client = Game.client;
        if (GameState.order == client.getOrder()) {
            client.sendMessage(new Protocol(ProtocolType.GET, client.getSocket().toString()));
        }
    }

    public void handleUno() {
        Client client = Game.client;
        client.sendMessage(new Protocol(ProtocolType.UNO, client.getPlayer().getPlayerCards().size() + ":" + client.getOrder()));
    }
}
