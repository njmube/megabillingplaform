'use strict';

describe('Controller Tests', function() {

    describe('Freecom_charge Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_charge, MockFreecom_airline;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_charge = jasmine.createSpy('MockFreecom_charge');
            MockFreecom_airline = jasmine.createSpy('MockFreecom_airline');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_charge': MockFreecom_charge,
                'Freecom_airline': MockFreecom_airline
            };
            createController = function() {
                $injector.get('$controller')("Freecom_chargeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_chargeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
