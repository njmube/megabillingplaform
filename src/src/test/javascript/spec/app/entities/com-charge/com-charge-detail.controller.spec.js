'use strict';

describe('Controller Tests', function() {

    describe('Com_charge Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_charge, MockCom_airline;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_charge = jasmine.createSpy('MockCom_charge');
            MockCom_airline = jasmine.createSpy('MockCom_airline');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_charge': MockCom_charge,
                'Com_airline': MockCom_airline
            };
            createController = function() {
                $injector.get('$controller')("Com_chargeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_chargeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
