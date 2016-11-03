'use strict';

describe('Controller Tests', function() {

    describe('Com_ecc_11_transfer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_ecc_11_transfer, MockCom_ecc_11_concept;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_ecc_11_transfer = jasmine.createSpy('MockCom_ecc_11_transfer');
            MockCom_ecc_11_concept = jasmine.createSpy('MockCom_ecc_11_concept');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_ecc_11_transfer': MockCom_ecc_11_transfer,
                'Com_ecc_11_concept': MockCom_ecc_11_concept
            };
            createController = function() {
                $injector.get('$controller')("Com_ecc_11_transferDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_ecc_11_transferUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
