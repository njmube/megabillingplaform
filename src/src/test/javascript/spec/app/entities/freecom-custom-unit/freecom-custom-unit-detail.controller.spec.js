'use strict';

describe('Controller Tests', function() {

    describe('Freecom_custom_unit Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_custom_unit;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_custom_unit = jasmine.createSpy('MockFreecom_custom_unit');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_custom_unit': MockFreecom_custom_unit
            };
            createController = function() {
                $injector.get('$controller')("Freecom_custom_unitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_custom_unitUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
