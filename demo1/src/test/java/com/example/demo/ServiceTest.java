package com.example.demo;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.Repo.ZamowienieRepository;
import com.example.demo.Services.ServiceZamowienie;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ServiceTest {

    @Mock
    private ZamowienieRepository zamowienieRepository;

    @InjectMocks
    private ServiceZamowienie serviceZamowienie;

    public ServiceTest() {
        MockitoAnnotations.openMocks(this);
    }


    /*
    @Test
    public void testCreateZamowienie() {
        Zamowienie zamowienie = new Zamowienie(1,"a","b",1);
        when(zamowienieRepository.save(any(Zamowienie.class))).thenReturn(zamowienie);

        Zamowienie result = serviceZamowienie.createZamowienie(zamowienie);

        assertNotNull(result);
        verify(zamowienieRepository, times(1)).save(zamowienie);
    }

     */


}
