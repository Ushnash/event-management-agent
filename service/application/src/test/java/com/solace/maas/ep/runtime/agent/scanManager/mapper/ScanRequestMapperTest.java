package com.solace.maas.ep.runtime.agent.scanManager.mapper;

import com.solace.maas.ep.runtime.agent.TestConfig;
import com.solace.maas.ep.runtime.agent.model.User;
import com.solace.maas.ep.common.model.ScanType;
import com.solace.maas.ep.runtime.agent.scanManager.model.ScanRequestBO;
import com.solace.maas.ep.common.model.ScanRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

@ActiveProfiles("TEST")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
public class ScanRequestMapperTest {

    @Autowired
    ScanRequestMapper scanRequestMapper;

    @Test
    public void testMapper() {
        ScanRequestDTO scanRequestDTO = new ScanRequestDTO(ScanType.ONETIME, List.of("TOPICS"), List.of());

        scanRequestMapper.map(scanRequestDTO);
        scanRequestMapper.map((ScanRequestDTO) null);

        ScanRequestBO scanRequestBO = new ScanRequestBO("id", ScanType.ONETIME,
                List.of("TEST_SCAN"), List.of());

        scanRequestMapper.map(scanRequestBO);
        scanRequestMapper.map((ScanRequestBO) null);

        assertThatNoException();
    }

    @Test
    public void testMapperWithUser() {
        User user = new User("orgId", "userId");
        ScanRequestBO scanRequestBO = new ScanRequestBO("id", ScanType.ONETIME,
                List.of("TEST_SCAN"), List.of());

        scanRequestMapper.map(scanRequestBO, user);
        scanRequestMapper.map(null, null);
        scanRequestMapper.map((ScanRequestBO) null);

        assertThatNoException();
    }
}
