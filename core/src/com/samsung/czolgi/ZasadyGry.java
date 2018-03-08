package com.samsung.czolgi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

/**
 *
 */

public class ZasadyGry extends DefaultStateMachine<GraCzolgi, ZasadyGry.Stan> {

    private static final String TAG = "ZasadyGry";

    public void nowaGra() {

    }

    enum Stan implements State<GraCzolgi> {
        TURA_GRACZA {
            @Override
            public void enter(GraCzolgi entity) {
                super.enter(entity);
            }
        },

        TURA_PRZECIWNIKA {
            // todo
        },

        KONIEC_GRY {
            // todo
        };


        @Override
        public void enter(GraCzolgi entity) {
            Gdx.app.log(TAG, "enter " + name());
        }

        @Override
        public void update(GraCzolgi entity) {
            Gdx.app.log(TAG, "update " + name());
        }

        @Override
        public void exit(GraCzolgi entity) {
            Gdx.app.log(TAG, "exit " + name());
        }

        @Override
        public boolean onMessage(GraCzolgi entity, Telegram telegram) {
            return false;
        }
    }

    public ZasadyGry(GraCzolgi graCzolgi) {
        setOwner(graCzolgi);
        setInitialState(Stan.TURA_GRACZA);
    }
}
