package com.codeblub.talkingthroughaid;

import com.badlogic.gdx.Game;
import com.codeblub.talkingthroughaid.screens.LoginScreen;

public class TalkingThroughAidGame extends Game {
    @Override
    public void create() {
        setScreen(new LoginScreen(this));
    }
}
