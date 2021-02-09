package com.udemy.microservices.config.messageconverter;

import com.udemy.microservices.model.User;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class UserMessageConverter extends AbstractHttpMessageConverter<User> {

    public UserMessageConverter() {
        super(new MediaType("text","user"));
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    protected User readInternal(Class<? extends User> aClass, HttpInputMessage httpInputMessage)
            throws IOException, HttpMessageNotReadableException {
        Scanner scanner = new Scanner(httpInputMessage.getBody(),"UTF-8");
        String requestMessage = scanner.useDelimiter("\\A").next();

        System.out.println(requestMessage);

        int i = requestMessage.indexOf("\n");
        if(i == -1){
            throw  new HttpMessageNotReadableException("No First Line Found",httpInputMessage);
        }
        String email = requestMessage.substring(0,1).trim();
        String firstname = requestMessage.substring(i).split(" ")[0].trim();
        String lastname = requestMessage.substring(i).split(" ")[1].trim();

        User user = new User();
        user.setEmail(email);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        return user;
    }

    @Override
    protected void writeInternal(User user, HttpOutputMessage httpOutputMessage)
            throws IOException, HttpMessageNotWritableException {
        OutputStream outputStream = httpOutputMessage.getBody();
        String body = user.getEmail() + "\n" + user.getFirstname() + "\n" + user.getLastname();
        outputStream.write(body.getBytes());
        outputStream.close();
    }
}
