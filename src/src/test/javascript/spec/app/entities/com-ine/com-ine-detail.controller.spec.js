'use strict';

describe('Controller Tests', function() {

    describe('Com_ine Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_ine, MockCfdi, MockC_committee_type, MockC_process_type;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_ine = jasmine.createSpy('MockCom_ine');
            MockCfdi = jasmine.createSpy('MockCfdi');
            MockC_committee_type = jasmine.createSpy('MockC_committee_type');
            MockC_process_type = jasmine.createSpy('MockC_process_type');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_ine': MockCom_ine,
                'Cfdi': MockCfdi,
                'C_committee_type': MockC_committee_type,
                'C_process_type': MockC_process_type
            };
            createController = function() {
                $injector.get('$controller')("Com_ineDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_ineUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
