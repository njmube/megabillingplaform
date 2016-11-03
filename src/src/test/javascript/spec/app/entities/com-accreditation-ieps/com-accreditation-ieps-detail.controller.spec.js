'use strict';

describe('Controller Tests', function() {

    describe('Com_accreditation_ieps Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_accreditation_ieps, MockC_tar, MockCfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_accreditation_ieps = jasmine.createSpy('MockCom_accreditation_ieps');
            MockC_tar = jasmine.createSpy('MockC_tar');
            MockCfdi = jasmine.createSpy('MockCfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_accreditation_ieps': MockCom_accreditation_ieps,
                'C_tar': MockC_tar,
                'Cfdi': MockCfdi
            };
            createController = function() {
                $injector.get('$controller')("Com_accreditation_iepsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_accreditation_iepsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
