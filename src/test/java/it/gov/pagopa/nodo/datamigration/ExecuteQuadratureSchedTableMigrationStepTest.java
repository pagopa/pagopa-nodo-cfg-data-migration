package it.gov.pagopa.nodo.datamigration;

import static org.junit.jupiter.api.Assertions.assertThrows;

/*
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = Application.class)
class ExecuteQuadratureSchedTableMigrationStepTest {

    @MockBean
    QuadratureSchedSrcRepository srcRepo;

    @MockBean
    QuadratureSchedDestRepository destRepo;

    @MockBean
    CfgDataMigrationRepository dataMigrationRepository;

    @InjectMocks
    @Autowired
    @Spy
    ExecuteQuadratureSchedTableMigrationStep migrationStep;

    @Mock
    FSMSharedState sharedState;

    DataMigration dataMigration;
    DataMigrationDetails dataMigrationDetails;
    DataMigrationStatus dataMigrationStatus;
    static int pageSize;

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        dataMigrationStatus = new DataMigrationStatus();
        dataMigrationDetails = new DataMigrationDetails();
        dataMigration = new DataMigration();

        Field statusField = DataMigrationDetails.class.getDeclaredField("quadratureSched");
        statusField.setAccessible(true);
        statusField.set(dataMigrationDetails, dataMigrationStatus);

        Field detailsField = DataMigration.class.getDeclaredField("details");
        detailsField.setAccessible(true);
        detailsField.set(dataMigration, dataMigrationDetails);

        when(dataMigrationRepository.findById("1")).thenReturn(Optional.of(dataMigration));


    }

    @Test
    void testExecuteStep() throws MigrationStepException, IllegalAccessException, NoSuchFieldException {
        QuadratureSched quadratureSched = new QuadratureSched();
        Page<QuadratureSched> pagedResponse = new PageImpl<>(Collections.singletonList(quadratureSched));

        // Emulating findAll
        Field field = ExecuteQuadratureSchedTableMigrationStep.class.getDeclaredField("PAGE_SIZE");
        field.setAccessible(true);
        pageSize = (int) field.get(null);
        Pageable pageable = PageRequest.of(0, pageSize);
        when(srcRepo.findAll(pageable)).thenReturn(pagedResponse);

        when(sharedState.getDataMigrationStateId()).thenReturn("1");
        when(dataMigrationRepository.findById(anyString())).thenReturn(Optional.of(dataMigration));

        migrationStep.executeStep();

        verify(srcRepo, times(1)).findAll(any(Pageable.class));
        verify(destRepo, times(1)).saveAllAndFlush(anyList());
    }

    @Test
    void testExecuteStepMigrationErrorOnStepException() throws MigrationStepException {
        Pageable pageable = PageRequest.of(0, pageSize);
        when(srcRepo.findAll(pageable)).thenThrow(new DataAccessException("Test Exception") {});
        when(sharedState.getDataMigrationStateId()).thenReturn("1");

        assertThrows(MigrationErrorOnStepException.class, () -> migrationStep.executeStep());

        verify(migrationStep, times(1)).updateDataMigrationStatusOnFailure(any());
    }

    @Test
    void testExecuteStepMigrationInterruptedStepException() throws MigrationStepException {
        when(sharedState.isBlockRequested()).thenReturn(true);
        when(sharedState.getDataMigrationStateId()).thenReturn("1");

        assertThrows(MigrationInterruptedStepException.class, () -> migrationStep.executeStep());

        verify(migrationStep, times(1)).updateDataMigrationStatusOnBlock(any());
    }

    @Test
    void getNextState() {
        StepName nextState = migrationStep.getNextState();
        assert nextState == StepName.EXECUTE_INTERMEDIARI_PA_TABLE_MIGRATION;
    }

    @Test
    void getStepName() {
        String stepName = migrationStep.getStepName();
        assert stepName.equals("EXECUTE_QUADRATURE_SCHED_TABLE_MIGRATION");
    }
}
*/
