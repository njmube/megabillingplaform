'use strict';

describe('Controller Tests', function() {

    describe('Com_apaw Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_apaw, MockCfdi, MockC_well_type, MockC_acquired_title, MockC_features_work_piece;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_apaw = jasmine.createSpy('MockCom_apaw');
            MockCfdi = jasmine.createSpy('MockCfdi');
            MockC_well_type = jasmine.createSpy('MockC_well_type');
            MockC_acquired_title = jasmine.createSpy('MockC_acquired_title');
            MockC_features_work_piece = jasmine.createSpy('MockC_features_work_piece');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_apaw': MockCom_apaw,
                'Cfdi': MockCfdi,
                'C_well_type': MockC_well_type,
                'C_acquired_title': MockC_acquired_title,
                'C_features_work_piece': MockC_features_work_piece
            };
            createController = function() {
                $injector.get('$controller')("Com_apawDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_apawUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
