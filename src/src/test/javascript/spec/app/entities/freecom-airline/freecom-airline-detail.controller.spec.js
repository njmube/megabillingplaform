'use strict';

describe('Controller Tests', function() {

    describe('Freecom_airline Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_airline, MockFree_cfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_airline = jasmine.createSpy('MockFreecom_airline');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_airline': MockFreecom_airline,
                'Free_cfdi': MockFree_cfdi
            };
            createController = function() {
                $injector.get('$controller')("Freecom_airlineDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_airlineUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
