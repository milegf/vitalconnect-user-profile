package com.vitalconnect.user_profile;

import com.vitalconnect.user_profile.exception.ResourceNotFoundException;
import com.vitalconnect.user_profile.model.UserProfile;
import com.vitalconnect.user_profile.repository.UserProfileRepository;
import com.vitalconnect.user_profile.service.UserProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserProfileServiceTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileService userProfileService;

    private UserProfile userProfile;

    @BeforeEach
    void setUp() {
        userProfile = new UserProfile();
        userProfile.setId(12345);
        userProfile.setNombre("Javiera");
        userProfile.setRol("PACIENTE");
    }

    @Test
    void testCreateUserProfile() {
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        UserProfile created = userProfileService.createUserProfile(userProfile);

        assertNotNull(created);
        assertEquals("Javiera", created.getNombre());
    }

    @Test
    void testGetAllUserProfiles() {
        when(userProfileRepository.findAll()).thenReturn(Arrays.asList(userProfile));

        List<UserProfile> list = userProfileService.getAllUserProfiles();

        assertEquals(1, list.size());
        assertEquals("Javiera", list.get(0).getNombre());
    }

    @Test
    void testGetUserProfileById() {
        when(userProfileRepository.findById(12345)).thenReturn(Optional.of(userProfile));

        UserProfile found = userProfileService.getUserProfileById(12345);

        assertEquals("Javiera", found.getNombre());
    }

    @Test
    void testGetUserProfileById_NotFound() {
        when(userProfileRepository.findById(54321)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userProfileService.getUserProfileById(54321));
    }

    @Test
    void testUpdateUserProfile() {
        UserProfile updatedProfile = new UserProfile();
        updatedProfile.setNombre("Marcelo");
        updatedProfile.setRol("PACIENTE");

        when(userProfileRepository.findById(98765)).thenReturn(Optional.of(userProfile));
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(updatedProfile);

        UserProfile result = userProfileService.updateUserProfile(98765, updatedProfile);

        assertEquals("Marcelo", result.getNombre());
    }

    @Test
    void testDeleteUserProfile() {
        int id = 30245;
        when(userProfileRepository.existsById(id)).thenReturn(true);
        doNothing().when(userProfileRepository).deleteById(id);
        userProfileService.deleteUserProfile(id);
        verify(userProfileRepository, times(1)).deleteById(id);
    }
}