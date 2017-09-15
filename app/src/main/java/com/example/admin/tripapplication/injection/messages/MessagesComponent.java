package com.example.admin.tripapplication.injection.messages;

import com.example.admin.tripapplication.view.messagesview.MessagesView;

import dagger.Component;

@Component(modules = MessagesModule.class)  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface MessagesComponent {

    void inject(MessagesView messagesView);

}
