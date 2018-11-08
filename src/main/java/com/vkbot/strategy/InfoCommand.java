package com.vkbot.strategy;


import com.vk.api.sdk.objects.messages.Message;
import com.vkbot.entity.MessagesToSend;

public class InfoCommand extends AbstractCommand{

    private Person person;
    private int iterator;

    @Override
    public MessagesToSend execute(String user) {
        messagesToSend = phraseUtil.choosePerson();
        return completeExecution();
    }

    @Override
    public MessagesToSend nextPhase(String user) {
        Message message = messageInstance.get();
        if(person == null || !person.hasNext(iterator)){
            choosePerson(message.getBody());
            return completeExecution();
        } else {
            return getInfo(person);
        }
    }

    private void choosePerson(String message) {
        if(message.equals(Person.NASTYA.getName())){
            getInfo(Person.NASTYA);
        } else if(message.equals(Person.ALENA.getName())){
            getInfo(Person.ALENA);
        } else if(message.equals(Person.DIMA.getName())){
            getInfo(Person.DIMA);
        } else if(message.equals(Person.JENYA.getName())){
            getInfo(Person.JENYA);
        } else if(message.equals(Person.LESHA.getName())){
            getInfo(Person.LESHA);
        } else if(message.equals(Person.OSCAR.getName())){
            getInfo(Person.OSCAR);
        } else {
            messagesToSend = phraseUtil.choosePerson();
            iterator = 0;
            person = null;
        }
    }

    private MessagesToSend getInfo(Person person){
        this.person = person;
        if (person.hasNext(iterator)){
            messagesToSend = phraseUtil.personInfo(person.name(), ++iterator);
        } else {
            messagesToSend = phraseUtil.choosePerson();
            iterator = 0;
            this.person = null;
        }
        return completeExecution();
    }

    public enum Person{
        OSCAR(3, "Оскар"),
        NASTYA(3, "Настя"),
        ALENA(3, "Алена"),
        DIMA(4,"Дима"),
        JENYA(3, "Женя"),
        LESHA(4, "Леша");

        int count;

        String name;

        Person(int i, String name) {
            count = i;
            this.name = name;
        }

        public boolean hasNext(int iterator){
            return iterator < count;
        }

        public String getName() {
            return name;
        }

    }
}
