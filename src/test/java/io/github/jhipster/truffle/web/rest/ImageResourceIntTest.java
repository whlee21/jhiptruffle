package io.github.jhipster.truffle.web.rest;

import io.github.jhipster.truffle.JhiptruffleApp;

import io.github.jhipster.truffle.service.ImageService;
import io.github.jhipster.truffle.service.dto.ImageDTO;
import io.github.jhipster.truffle.service.mapper.ImageMapper;
import io.github.jhipster.truffle.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.truffle.web.rest.vm.LoggerVM;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.truffle.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ImageResource REST controller.
 *
 * @see ImageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhiptruffleApp.class)
@ActiveProfiles("dev")
// @TestPropertySource(locations = "classpath:./config/application-dev.yml")
public class ImageResourceIntTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String DEFAULT_CRYPTO_USER = "AAAAAAAAAA";
    // private static final String UPDATED_CRYPTO_USER = "BBBBBBBBBB";
    private static final String UPDATED_CRYPTO_USER = "0xbe04a131B74767c48F25dDa4248bb70894EDE1d5";

    private static final String DEFAULT_IMAGE_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_LOCATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_UPVOTE_COUNT = 1;
    private static final Integer UPDATED_UPVOTE_COUNT = 2;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImageMockMvc;

    @Autowired
    private MockServletContext mockServletContext;

    private ImageDTO imageDTO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImageResource imageResource = new ImageResource(imageService, mockServletContext);
        this.restImageMockMvc = MockMvcBuilders.standaloneSetup(imageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImageDTO createDTO(EntityManager em) {
        ImageDTO imageDTO = new ImageDTO()
            .cryptoUser(DEFAULT_CRYPTO_USER)
            .imageLocation(DEFAULT_IMAGE_LOCATION)
            .upvoteCount(DEFAULT_UPVOTE_COUNT);
        return imageDTO;
    }

    @Before
    public void initTest() {
        imageDTO = createDTO(em);
    }

    @Test
    @Transactional
    public void createImage() throws Exception {
        int databaseSizeBeforeCreate = imageService.findAll().size();

        String imageBase64 = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxISEhUSEBISFhUWFxYVFRgWFRUXFxcVFxcdFxcYFx0YHSggGBolGxYXITEhJSkrLi4uFx8zODMtNygtLisBCgoKDg0OGxAQGi0lHyUtLS0tLS0tKy0tLS0tLS0tLS0tLS0rLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIAJ0BQgMBEQACEQEDEQH/xAAbAAEAAgMBAQAAAAAAAAAAAAAABAUBAwYHAv/EAEYQAAEDAQUBCwoDBgYDAQAAAAEAAhEDBAUSITFBBhMUIjJRYXFzobIzNEJSgZGSsdHhYnLBFSNTk6LwB0OC0uLxFmOzJP/EABoBAQACAwEAAAAAAAAAAAAAAAABBQIDBAb/xAA0EQACAQIFAwIFAgYCAwAAAAAAAQIDEQQSEzFRIUFhBTIiM3GBkRRSFaGxweHwI9FCgpL/2gAMAwEAAhEDEQA/AKy3W+tv9QCrVjfXiMbojGctVDvYfU9AqNna4dTnD5FcEMZVg+j/ACJ4KlPdfgh17LU1ZVf1Fx+asKPqcNqkfuitr+lVF1pTf0ZW1LRVaYc94I/EfqreGnNXj1RSTdWDyybTPnhb/Xf8Tvqssi4MNWfLHC3+u/4nJkjwNWfLHC3+u/4nJkjwNWfLHC3+u/4nJkjwNWfLHC3+u/4nJkjwNWfLHC3+u/4nJkjwNWfLHC3+u/4nJkjwNWfLHC3+u/4nfVMkeBqz5ZLsVseG1nTiLWMLccuAJqNaTE8xK11IK6SOqhUkoylvZFrcFj36hVqubaKr21Q1rKdbe+KWtJjE4NyxHatNeWSVkdWEpqrTcpXbv2djdaLucGuIsduaQ0kONrpEAxqQKuYHMsI1Oq+Jfg2zoJRfwS/+v8ldddNj7PUtFevaGtY9rP3ZmcQbBh3S5b6l1NRjFbHJhoKdJ1Jyas+xh9ossHDaLdMGJDYnZKhRqftQlLD26TlclbmrGa9Oo+pjdgcBlVrg5tByFOZ1WOIlkkkv7G3BU9WDlLnllo666Qa8llcENJbhfbDxoynEAIWhVHdf4Ox0IZXv+WcWLc+JNR+k8o/VWGSPBQ6k+zZ0dO62h1OhVtVZtpqMxBok02kglrXHWcjt2dS5XVfWSirIs44aPSE5vOyDhLbHVqPc8VadpFGd8fAADcQiYOZOcLO6dRJbWuacklh5Nt5k7Hy2i82XhAqVi81t6DQSQREggDOZUuSVTLZWsYqnJ0NRSd72sWdS7w2g57t+DxYnVjiqVAW1RtgnLqK0qd527XO2VBRpN9b5W9+5EY2nRo0q1qqWh5rSadOk/DxRElziRnmMp27YJWxtym4wSVjnjGFKkqlVt32SPi2NY6zutVlqWgNpuDKlOo6XAugAtcDnym7Tr0QkW1PJNIxqxUqOrSbVt0yfZRZA6hQe6vWq1QMVSnWdhY52yA/ZB2HLMrXLO7y2SOmnGinGm7tvvcriX0q1egX4wxtaHGcUtpFzSDORGU9MrY0pRU7cHOnKFSdJu6V/qVfC3+u/4nfVdGRcFfqz5HC3+u/4nJkjwNWfLHC3+u/4nJkjwNWfLHC3+u/4nJkjwNWfLHC3+u/4nJkXA1Z8s20X1n8g1D1Od9UyR4J1KnLJbbDaT6Th11D+hTLHgnNU5M8AtPrn+YUyx4JzVOT5NjtPO7+Z90yx4IzVOTLbFaedw66n3TLHgZqnJIoXbV9Os4flc4nvTLHgnNPk4m+bXVZaKrW1aoDXuA/eP0B61U1fez0+G+VH6HZ3VXcaFIlziTTZJLjJ4o6Vgbjgbf5ep2r/ABlQ9gellU51GAoSBuvjc6eI19Siyo84aeJ5GIxiLRlnkCfZ0q3wMp0H19rKzH4aOIj8PuIv/g9q9aj8Tv8AarX9XDhlR/C6vKNNq3I1qTcVWrZ2NlrZc8gYnHC0SW6kkBP1kB/C6vKIF93LVsuDfSw48UYSTyYmZA51upVlUvY5sRhZ0LZu5WYltOYYkAxIBiQDEhJKsjv3Vo/JT/8As1ap+6J0Uflz+n9y4uUUH0xip2ZrmkNcaltq0nPcAONhAiD+i5aykpb/AMixwjpunsl9y4twZXL6rqdkquYwucKduqk4Gj1WNAWlJx6f2OudpJtpP7lPdbrSKTxSsDalCs4VWtfxmgQMIBnMDCMyumooN9Z9UcFDVSaVO8ZO5aX5YHU6hZZ7tovZhaceATiMyNRpl71qpSUleU7HRXpuLtCkmrbkPc1Tdvb2PosBpODHneg5xMf5hNVvGz+SmvbNdO9zHB3UXFxtbwWzqTcLzFFuFjnDHSABgTGVcrSt0jrezf8Av9Tz+tVx4iGtbizwtENbOxo2BWkY2Vjzc5XnmSOxc6hWtFK38Iospta11VjnRUbUY0gNDdskj3ZTK4fijF07dS5tTnUjXzKyXXkjWa93tsVqr0w2X2w5PEgNe1nuMHVZOknUjF8GuNd6E5rvLubqVazUqDbBv7WvDRW4QCDTbXBD2iduQ90bSFi4zlLUt02sbIypU6ehms7Xv5Jdes+pZXOeS57rteSdpcRroPkFgklP/wBjdLNOly3Fmm6rFa2WWo2pZxUwubvVGq1jhBPGLTOUSdqzqzpuonF25Zpw9OtGg1ON7bJ2PjdBYbW+hQayhhYWl9WlSaxrWPEROcnUn2dSmjOmptyf0ZGLpVpU4qMendKxUbn7eyjSr1Wtca2ECk7envaydSXBpaw5+kRMBba8XOUY9jlwk1Spynb4uxDu6oXVKhcSSaVoJJ1JNJxJPStlVJRSXg04aTlOTb7MhYluOQYkAxIBiQFpct3b6cT+QMvzHm6lDZlGNzpWMAEAAAbAsTYfUIBCAQgEIAgPK90HnVftH/NVNX3s9LhvlR+h2t0eQpdmzwhazecLb/L1O1f4yoewPSyqY6gFK3DLzdDuVFqrU6pqOEQ1wk5MBxTT9V0gZ7DDuUxkXC2OU4fdHuytAe/eqjqdKmSxjWxJDThBcTJJMLkg6tetpwdj1kMDhMJhNavHM7Xf/SJ+561Ovmg7G7DaLM5oa8zhLHnNxa0hu+Q17Q6DEmQWuc02NTD1KFlNpnl6tahVlmoppcPt/gkbvLLvNOy08bn4W1BieZcc28/d0QurA9yj9V/8fucjiVhYpxiQGxlF50Y89TSf0UdBYPovGrHjraR+idBY14lNgb7LbCzGMFN4eA1weHEQHBwjC5pmQNq1zpqVutrG2nVyXVk0+TQXdA+nQs0jU97m+x259LHvZAxsNN2QMtOoz0WM6ana/Y2U6sqV8vcxTttVoDW1aoAyAFR4AHQAckdOL3QVWaVlJ/kz+0K38at/NqfVNOHCJ1qn7n+TfYr6r0g4U6mTzifia1+J2knGDzLGdCEndmdPFVKaaT3N3/klp9dn8ml/tWP6eBm8bV8fgqgVvSOTuJQixIbbnikaII3tzxUIgTjAABnX0RktemnLN3NqqyVPJ2I8rYa7E1l71g0s3wlppmjBAypn0W5Zda1OhBu9joWKqpWv0tY08Oq/xq382p/uWWnDhfg16tT9z/LHDav8at/NqfVNOPCI1Z/uf5Z92e8ajKdSixwDKkYxAzjp1CiVOLkpPdGUK8oQcFs9z5s1tLA4BlMlzXNxODi4Ne3C4NhwGhOoKTp5nuTTraadkuv5NGJbDRYYksSMSWBsstF1R4Y3Un3dJ6FD6IWO5s1AMa1jdAI+613NiRtQkIAgCAIDCEHle6Dzqv2j/mqmr72elw3yo/Q7W6PIUuzZ4QtZvOFt/l6nav8AGVD2B6WVTnWEW5Ba7pr/ALRZ7RTYynLTGFuEuNoJgOpsIHFc0Eugxk3Fm0PLLhbHKc/uo/w2rV6rqlmrsYyocbqVQHiOOZhzJkTnB054hdWGnQpPM4deTZicTiq1JUpT+HglULqdc9lDKRcX1nA1bThGBjhAYwtMlrTJAMHbq9zGuxxFd1pXOenTyIn7oKLq1KzutVINqYX4mg6GR6pgSADEmNJMSeGrialH2O1zJ4anW96vYp2XfSGlNntE/NaP4hiL3cif4fh7WUUTqBY3RjW/lACsKXqqbtNFbV9IaV4P7ExWyaauioas7MKSCPabDTqctjT0xn7xmpuRYo7w3NbaDv8AS4/I/VZKfJDRztZjmEteCCNQRBWwxPjEgGJAMSAYkAxIBiQDEgGJLAtruuKrV4zuI3nIzPUPqsXJEpF9Z9z9BurS8/iJ+QyWGZk2JbbtojSlT+EKLk2R8vuqgdaTPYI+SXFkRK25ygeTib1On5ypzsixX19y7vQqA/mBHeJU5xlIVS4LQPQDupw/WFkpojKzV+xrR/Cd72/VTmQsSbNucrO5eFg6TJ9w+qxc0LHR3bddOiOLm46uOp6OgLBu5lYmwoJEIBCAQgEIBCAQgPKt0HnVftH/ADVTV97PSYb5UfodpdHkKXZs8IWBvOFt/l6nav8AGVD2CPSyqY6zAQG+hedYtaTVeTAOu2FnCtPLuRKEb7Gz9p1v4r/ep1Z8kZI8A3lW21Xe9TrT5GVcGqvaXvjG4ujSdk/9LCU5S3ZKSRpWJIQEiz1oyKtsBjVBadT7Mp/UMC5vUp790SQVdxmpK6ZRSi4u0lYysiDCAjW6wU6wio2eY6EdRRNog5e8NzdVkmn+8bzaOHs2+z3LapruRYpHggw4EHmIg96zIMYkAxIBiQCUBvsdlfVdhptLj3DrOxQ2luLHW3TufZSh1SHv/pb1DaekrVKVzKxdLEGEJMoQEAQBCQgMIQEBlAEAQBAEAQBAEB5Tuh86r9q/5qpqe9npcN8qP0O0ujyFLs2eELA3nC2/y9TtX+MqHsEellUx1hAQqlsFNtIYXuL+K0Mwzk3EZxOAAgFYw2MpbkR1+tioQ3kRPGGpqVKcOgZeTnbygszGxlt+tgEtMlgcACOMcYYQ2YnXUxoUsLG9t58YNLC043sOJzciymKmokQZG1GgYN5EPptexoxuwgiqx0HCXZjm4veFALBACgPuk8g/NdOFrypTVn0OXFYeFWDuupYQvU3PJ2CAIAgNVezseIe1rh+IA/NAVtbc3ZnegW/lcR3aLJTkLER+5KlsqVB8J/RZajIsYZuRp7ajz7AE1GLEyhubs7dWl35nEj3DJY52TYtKVJrRhaABzAADuWIPtAEAQBAEAQBAEAQBAEAQBAEAQBAEAQHlG6Lzqv2r/mqqp72ekw3yo/Q7S6PIUuzZ4QsDecLb/L1O1f4yoewR6WVTnWAoBCtFhLhT472OpkkFmCc2lp5bSNDzKIqyJb6leLleBVALCXBhaSS3jtrVamIlokHDUaJG0dCyIubqd3OwvBp0mzTcxv7x1QYnOxy7GwScRkkkoSZs93u4gfSota3EeI4mXObhJLTTDT7/AHoQfFG5MFQPa/FLgagfTogFoaQMOGmCDIZpG1QC5QGEBkFTGWVp8GM45ouPJYUqmISvUYbEKtDMeUxOHdCpl7H2uk5hCEiEAhAIQCEAhAIQCEAhAIQCEAhAIQCEAhAIQCEAhAIQCEAhAIQCEAhAIQCEIPJ90XnVftX/ADVVU9zPSYb5UfodpdHkKXZs8IWBvOFt/l6nav8AGVD2CPSyqY6zCEBAEBlCTCAIQEAQBCTdZqkHoOq7cDX0qnXZ9Dhx+H1ad1uievSHmAgCAw9waJcQANSTA70BGo3jRcYbVYTMa69U6+xLixrs9uc6s+lvRAZyn42nMiWiBnmM1BNicpICAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIDybdF51X7V/zVXU9zPSYb5UfodpdHkKXZs8IWBuOGt/l6nav8ZUPYI9SoUw5pGUzqtWFoxrUZRt15OXGV50K0ZX6Psa6tnI6lz1sDVpK7V0dFHH0qryp2bN1KygtBk5rrw/p8KlNSb3OPEepTp1XFJWRHq0y0wfYq/EYeVGVmWGGxEa0My+58LQdAQCEJCEBAEJCAsbNUxN6RkV6XB11VprlbnlsbQ0qr4fVG1dZxmHCckJPN7bRtdGoWF9R2A8VxdiAGwy6Qwkc8LJWZkYFvtmENIc5oJcGupMdJMyQ3DLuUc8xmlkCx3NWquLQAWgCpAqOOIg4Gw2DijHlEa5o9iGi2D7Rjqw2pEVYMP1ziJGenF1/VUd6+eVk7def9+hb2oZI3a7cf79TNlFowOkVCQ4ECXjEA08WcoEkZ84Slr5HvuRV0HNbbF7ZZwiQQemZ/qzPtVtSbcOpV1bZuhtW01hAEAQBAEAQBAEAQBAEAQBAEAQBAEB5Lui86r9q/wCaq6nuZ6PDfKj9DtLo8hS7NnhCwN5w1u84qdq/xlLX3Ibsro9dZRDZhd1DDwpL4e556viJ1n8XY+4W92a6mi9uqEIrJWQbv1I1vZkDzfqqz1SneCmuxa+k1bTcOSCqMvyJeJfDd7a4kODjBjitMkdJOkLCV+xlGxR3hWeAJcScdXYQOUJyJMbIGzNaZykjZFJl7dbiaTCSTxRqc/b0rfDqka5bkpZGIQgIDbZ6mF07Nq6sJXdKon2OXGUFWpPlbFkvSp3VzyzVnYKSDXUotPGLWlwBg4QT7FBNznqTqrqc722eM4t3luF0NbGrRxi55HRgOsSRkTrPaHmo0mztbo3FBDhkMs2aAmNfRKEFjaauEdJ0XJi8Toxut2deCwqry6vojRQrunjHLnOXsXJgq1ecuvtOzH0MPTjaPuJoVuU4JjMoCDVvei12F1RoPMTHzQWJlOoHCWmUB9IQEAQBAEAQBAEAQBAEAQBAEAQHku6Pzqv2r/mqyfuZ6TDfKj9Ds7o8hS7NnhCwNxw1u84qdq/xlStzGWzPYirRHmXuFJAQGq0UsQiVzYqjrQyp2OnC19GpnauQbTTDSAJmM+tUWKoxpSUV9y/wdedaLm9r9DSuU6wgMoDCAIAhJrr2hjOW9resgLFyS3ZNmWF22ttRvFc10bQQcvYr707FKpHI31R571LCOnPOl0Z9VG1MctmBhykAHJ8jPQyWZ9XSrB3uV6cbdSG6laPSLjAAljtSA7OCRAJwg69HOMXmNl4W6Gy1U68twF0BsPIcwSYyw4tDOZ5xt2I83YiLh1vyarJTtOP94XQHc4giZJy2RIE842osxlN07dC2LAdQFlKEZe5XNMZyj7Xb6GQFkkl0SMW292a+Dj0eL+XLp00PtCkgpd09eo1jabHZ1HtYCAZGI680gAxpnB2KUSjZTuyyUxg3hp9Yva1ziedxOp9q4a2PhTnl6t+Duo4CpVhn6Jf1I12s4PajQYXb25jajAZOAE4S2TsBgjmkjWV2p5o3OOUbOx0ikwCAIAgCAIAgCAIAgCAIAgCAKAeR7o/O6/av+aranuZ6PD/Kj9Ds7o8hS7NnhCwNxw1u84qdq/xlStzGWzPYyrNHmXuYUkBAYdpksZtpO25lBJySexWWl5JzEELzmLqyqTvJWZ6fB0oU4WhK6NS5TqCAIAgCAjXjad6pOfqQMuaTkJ96xlLLG5lFXdjlTdlWpTNodUpkkF2Eudvha3IkNw5tHOMhOoXOqE5xzXN+dRdiS27bTZJtALAGOwmHh2Ih+AtIGwkHXuK2U41cO1UXY1VYwrxdN9zu7PbGva17ZhwDuS46idgXsaU1OClyeMqU8k3F9jZvvM1/uj5wszCwxu9Q+1zR8pQAY+Zo9pP6BARX24NnE8DDMzTeNACcydsgjrWOdGapvsYFtpwCa4E8+BumW0T/AGEzIOnJO1iXvA2lx/1H9IWRgV99XQK1OGkh4gtdMkEZgiTqCO886m4K6ne9VkNqWQGppLHtDDlqA6CzqIWr9PTzZ7dTdr1MmW/Q+7pslTfXV68Me6GsbBwBo5LGn2k9eggLa/BqL4VNjhhPToeo/wBlRcg12q1BmRa85TxRPpNb1+mD1AqHKxlGGbY0/tSniwmQTyY42LIE4cMk5Ob71jqIz0ZdiVZq7ajcTDIPQR06HPQhZp3NcouO5sUmIQBAEAQGUBhAEAQBAEB5Huj87tHav+aranuZ6PD/ACo/Q7O6PIUuzZ4QsDccNbvOKnav8ZUrciWzPZCFZnmGYUN2CVyLXtgHJzPd91XV/UYxVqfVlph/TZyd6nRfzNNO2meNBHRqual6lNS+Pqjqrel03H4HZi31ASI2fqsfUasZySj2MvTKM4Rbl3IoVcWYQgSgCAISRb1spq0nsGpGXWDIHd3rCpHNGxlF2ZzNC/q9OmaJ5IaWwZDmmCAZGYwzp0LnVeUY5Wjc6afUzaLzdaBvYY/G8MbJqucOJGYa4cUEiTnqSspVs6ypbkKGXqd/ddPDRYz1Whvw5L1eAd6EVx0PI4+NsRL8/klrsOMIAgI3AacEQYJLuU7JzjJIzyM55LHKjPOz4ZdlJswyJmcznOZJz1yGeuQ5lGREurN9yWAszWVm6G0vp0xvbi1xcMw0u4o5Q5DgDHOEXUlFTY74rmlVINU1MAcxrqckEGHZNpNBMHSSpaVxZFhdVtNVxaarqjYcCDZnU2yMiC45TswqLAs3UCOQRHquzbt9o16RlEIDS9jTk4AbMLwC0iRk07M8OXcoJTa2NgYzIOY0GQQCBEzOR559vQlkMzPp72U4BhoOmUDIdHQAmwUZSPp9UYC9oLsiQG6ujYOlTcjK72ZC/acMLyyYcGjCXEOJ2AloMzlmInaouZZDFe9cIPEJjfNuUscWgSecNJ6NNSJXCgWcKTAQgJllu4vbixATpl91zyr5ZWsd9LBakFK5t/ZB9ce77qP1Pg2fw1/uH7IPrj3KP1Pgfw1/uNdpu7A0uxTpsWcK+aVrGqtgtOGa5BhbzhMwgPId0nndo7V/zVbU9zPRYf5UfodndHkKXZs8IWJuOGt3nFTtX+MqVuRLZnspVmjzLMESsZLMmiYtpprsQK1hgS0z81TVvTnFOUHcu6HqalJRmreSGqwtggCAIQEBlAYQkBARbXd1KoZqMBPPmD7xmVhKEZbolSa2PuyWKnT8mxo5zmSesnNTGCWwcm9y5u/ke0r0HprvS+5531RWrfYlKwK0IAgCAIAgNVpszKgw1Gtc2ZgiRIQEJtyUQXFoc0ODQQxzqY4hJBGCDPGM57AhJ9WW56NNwczfAQS7OrVIJOpILoJPSlyCwQGCJ1Qk0GgQIZEeo7NukQNrdnONclAK2+PRycIJEHTog6GcMxqMpAlQzow7XU+LpqkneyJaZJHNGaJmdaPS/ct6dkY3ksAgyMtDnmOY5n3rI5bs1VbupO5TfW9Jw5Ti52h2kkqLBNkl7gBJIA5yhCTexX171AyYJ6TkPqoub4UG9zqLhql9BjiImfEVw1fcy5wytTSPOv8AFG/a/CODMe5lNrGudhJaXudJzIzgAaaZlVuJqPNlR7H0PB0nS1pK7bsvBD/w4v6uy106Be99KriaWucXYXBpcHNnTkwRpmscPUallZu9YwdJ0HUStJfzR6pe7wKTi4wMsz1hWtH3o8LjE3RaRzr7dTHpA9WZ7l3XKVUpvsQK97H0AAOc5n7KLm+NBLc80vt5NorE6mo4n3rgn7mXNFWppeDt7o8hS7NnhCwNhw1u84qdq/xlStyJbM9mKskeZYQBGCotbYeeuf7715vFwyVmj1GCqZ6MWaVzHUEAQGUBhCAhIQBCAgLO7hxPaVfenK1L7nn/AFN3rW8Epd5XBAEAQCEAQBAFICAKAEBEvTHvfEmSc41jahspWzdTXYHOcwitGsQ6JiNCOfvWN0TUspXiSLNZ2NzpgZ7QZ6VKsYSlJ7n3WqhglxgKWyIxcnZFdVvY+i33me4fVY3OhUOSvrVXPMuJP97ENySWx8Nyicxzc6Es7646uOgx0ROLLqcR+i4qvuZYYZWppFPuv3G07cWvxmnVaMOIDEHN1AcJEwSYM7SuWrRVQusB6pPCXja8X2NG5LcLTsb9+fUNWrBDThwtYDrAk5nnJUUqCg7meP8AVp4qOmlaP9S53Tj/APM/rb4guul7kUeI+WzhoXaVwhAcFfHl6v53fNcNT3Ms6XsR3N0eQpdmzwhYGw4a3ecVO2f4ypIlsz2cqxPNMKSAoJK28+UOr9VSepL/AJF9C99Lf/E/qQ1XFoEAQBAEICA+K7iGuI1AJGROezIZlQ3ZErqyqbeVUse4AS0NjiPEEmHAzM4TkfatWo7GzIrkm6rY+pJcBGFuY9YjMd09CyhJvciUbHS2Ko3C0AiY029K9Jg6sNOMU+p5jG0p6sptdCQu04QgCAjXgJY5s5kGM4n27FMd7mFTrGxUtsLsGT2iC7LEBqBzZSt2dX2ObSll3LqyNhjRlk0AxnmBBWh7nXD2o2oSEAQBAEAhCSstN0Yy4l8YpEhsENOeHWD7QZk9Ea3C5ujVskj5ZcwBJNR0Fxe4Ditk6wAchr3cyZCda/YhWmqXQJJDQACdTAjEekrJG2EbI1QhkIQCFIFKq4EgOcBIyDiBp1rRBJ1JX8G6TahG3k2b8/13/E76rblXBrzy5G/P9d/xO+qZVwM8uT5dUcci5xHSSR3lLIhyfJ8QpIMwgOAvny9X87vmuGfuZaUvYjuLo8hS7NnhCwNhw1u84qdq/wAZUoiWzPaSrE80zCAICFeNAmHDZr1Ks9QoSmlOPYtPTcRGF4S7laqYvAhIQBCAgEoSfNSmHAtcJBTcEf8AZ1KCMAzBBiR08/OAscqJzM2WeysZOEGXakkkmNNSVKilsG7ljdkYj1GO5WPpttV/QrPVL6StyWivCgCAIDXVoNdyhPvUptEOKe58myMiMIiZ26/2AmZ3IyR4PunSDRDRAUPruSklsfaEhAEAQBAEBhxAEnIIErlVb7di4rNNp5+roUHRCnbqyvhDcZhCBCAxCAjWmtgDnRPGaMzAEgCSYMAbVph75fY3S9sfuaW25xAIZq2q7KTJpwAW5ZgzzLaaj6oW/G8NDNcWIzMQ2dmnt51IJsIQZhCTEIQefXz5xV/O75rhn7mWtL2I7i6PIUuzZ4QsDYcNbvOKnav8ZRbkS2Z7SVYnm2FICggjWi2BhiCSuPEYyNKWW12d+GwM60cydkVdapicTpKpKs1Oblaxe0aenBQvex8LWbBKAISEIMoSYQBAZQgnXUzNx6APfmrP02HxORVeqTtGMSxVwUgUkhAEAQBAYcQBJIAGZJ0A6VDdgld2Kq77/o1qppNxAgS0uEB424foVphXjJ2Oqrg5045mW0LccoQBSDXXqhjcR/7KgmMbspbRWc88Y9Q2BDpjFR2NUISIQkQhAhAMKEmmtQJa8AwXCAY0OGJjasIwtJvkylO6S4IDLoOeJ4Mtc0ZaExBiNhAWRFzdQu6HAltHIzxWuBnYcz1IRcnwpAwoQIQHnd9ecVu0d81w1Pcy2pexHb3R5Cl2bPCFgbDhrd5xU7V/jKLciWzPairE84EAUBFTbrRiJAaMjrtVHjMQqjcUtu5f4LDOmlJyfXsRFwlgEAQBAEICEhAZQGEILe7acMk7TPs2K9wFPJTu+55/1Gpnq24JULuOGx8NqtLi0OaXN1AIkTpI2KFJbEuLSu0fakgIAgCCxyG6C+xVmlS5HpH142D8Pz6ta+vXzfCti4wmEy/HLc5m10vSGxcqZYtFjd97VqLBgqGBxodxhO0Z5gdRC2Rqzjsc9TC0p7o7a57ybaKYqNy2Obta7aPoVZU6imrlHXoulKzJy2GkrLyrBxAGg15pUG+mrK5CwqTMYUAhAMKAYUAwoBCAYUAwoBhQDCgJlG7nHNxjvP2UGDmkeXboWRaq45qrx3rhn7mXNF3pp+DtLo8hS7NnhCxNpU2nctNVzt+1qOdG987pjlKUQ9j0khdWr4KZ4fyITV8Efp/JrtDeKQDGWq1VptwaXQ3UMOlUTbKvgX4u77qn0PJe5xwL8Xd91Gh5GbwY4H+Lu+6aHkZ/BngX4u77poeSM44F+Lu+6aHknN4HA/xd33U6HkZxwL8Xd91Gh5Iz+BwL8Xd900PIz+AbF+Lu+6aHkZ/Bts9hGLMyNYj7rdQw6c1c0Yio1B5ejJN41HNZxCA45AkTHTG1Ws6to2RV0sGpS6s5a0XbVdOO0vdmCAcUTzxi1XG3J9yyjTpx2iKV0vYcVOsWuEwQ3PPXbmojdO6ZnKMZRytdCzuzhDao3yuajXGC1zAI5i0g5fJb6dWWbqcdfC03D4VY6CF1ang4P03kQmr4I/TeSvvxrjRc1jsJdxcUTAOsZjMgR7Vqq1G4ux0YfCrOm2cpZ9zmH/Mz/J/yXFl8lvc2m4f/AGf0/wDJMnkXNI3PQ0t33+jn/wBSZRc3XXd1Wzux06wzyc0sycBsPG68+lbKbcHdM0VqUaitI6m3SW5GBhmI1y29C7NXpsVsMMr7lZdtneQRUqBx2EMw+zUrCnWk9zfVw0VbKTOB9Pd91s1fBp0PJg2Tp7vumr4Gh5HBOnu+6avgaHkcE6e77pq+BoeRwPp7vumr4Gh5HBOnu+6avgaHkcE6e77pq+BoeRwTp7vumr4Gh5HBOnu+6avgaHkcE6e77pq+BoeSVY7KG8bU7OhNXwYyoeSXCnU8Gv8ATeTzu+tzG+Wiq/foxVHOjBMSdJxZrkl1kW1KOWCXg6GwXZhpU245wsaJw6wAOdQbD//Z";
        imageDTO.setImageBase64(imageBase64);

        // Create the Image
        restImageMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isCreated());

        // Validate the Image in the database
        List<ImageDTO> imageList = imageService.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeCreate + 1);
        ImageDTO testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getCryptoUser()).isEqualTo(DEFAULT_CRYPTO_USER);
        // assertThat(testImage.getImageLocation()).isEqualTo(DEFAULT_IMAGE_LOCATION);
        assertThat(testImage.getUpvoteCount()).isEqualTo(DEFAULT_UPVOTE_COUNT);
    }

    @Test
    @Transactional
    public void createImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imageService.findAll().size();

        // Create the Image with an existing ID
        imageDTO.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<ImageDTO> imageList = imageService.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImages() throws Exception {
        // Initialize the database
        imageDTO = imageService.saveAndFlush(imageDTO);

        // Get all the imageList
        restImageMockMvc.perform(get("/api/images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imageDTO.getId().intValue())))
            .andExpect(jsonPath("$.[*].cryptoUser").value(hasItem(DEFAULT_CRYPTO_USER.toString())))
            .andExpect(jsonPath("$.[*].imageLocation").value(hasItem(DEFAULT_IMAGE_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].upvoteCount").value(hasItem(DEFAULT_UPVOTE_COUNT)));
    }
    

    @Test
    @Transactional
    public void getImage() throws Exception {
        // Initialize the database
        imageDTO = imageService.saveAndFlush(imageDTO);

        // Get the image
        restImageMockMvc.perform(get("/api/images/{id}", imageDTO.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imageDTO.getId().intValue()))
            .andExpect(jsonPath("$.cryptoUser").value(DEFAULT_CRYPTO_USER.toString()))
            .andExpect(jsonPath("$.imageLocation").value(DEFAULT_IMAGE_LOCATION.toString()))
            .andExpect(jsonPath("$.upvoteCount").value(DEFAULT_UPVOTE_COUNT));
    }
    @Test
    @Transactional
    public void getNonExistingImage() throws Exception {
        // Get the image
        restImageMockMvc.perform(get("/api/images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImage() throws Exception {
        // Initialize the database
        imageDTO = imageService.saveAndFlush(imageDTO);

        int databaseSizeBeforeUpdate = imageService.findAll().size();

        // Update the image
        ImageDTO updatedImage = imageService.findById(imageDTO.getId()).get();
        // Disconnect from session so that the updates on updatedImage are not directly saved in db
        em.detach(imageMapper.toEntity(updatedImage));
        updatedImage
            .cryptoUser(UPDATED_CRYPTO_USER)
            .imageLocation(UPDATED_IMAGE_LOCATION)
            .upvoteCount(UPDATED_UPVOTE_COUNT);

        restImageMockMvc.perform(put("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImage)))
            .andExpect(status().isOk());

        // Validate the Image in the database
        List<ImageDTO> imageList = imageService.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
        ImageDTO testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getCryptoUser()).isEqualTo(UPDATED_CRYPTO_USER);
        // assertThat(testImage.getImageLocation()).isEqualTo(UPDATED_IMAGE_LOCATION);
        assertThat(testImage.getUpvoteCount()).isEqualTo(UPDATED_UPVOTE_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingImage() throws Exception {
        int databaseSizeBeforeUpdate = imageService.findAll().size();

        // Create the Image

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImageMockMvc.perform(put("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<ImageDTO> imageList = imageService.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImage() throws Exception {
        // Initialize the database
        imageDTO = imageService.saveAndFlush(imageDTO);

        int databaseSizeBeforeDelete = imageService.findAll().size();

        // Get the image
        restImageMockMvc.perform(delete("/api/images/{id}", imageDTO.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ImageDTO> imageList = imageService.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageDTO.class);
        ImageDTO image1 = new ImageDTO();
        image1.setId(1L);
        ImageDTO image2 = new ImageDTO();
        image2.setId(image1.getId());
        assertThat(image1).isEqualTo(image2);
        image2.setId(2L);
        assertThat(image1).isNotEqualTo(image2);
        image1.setId(null);
        assertThat(image1).isNotEqualTo(image2);
    }
}
