package com.example.admin.tripapplication.injection.messages;

import com.example.admin.tripapplication.view.messagesview.MessagesPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MessagesModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    MessagesPresenter providesMessagesPresenter(){

        return new MessagesPresenter();
    }
}
