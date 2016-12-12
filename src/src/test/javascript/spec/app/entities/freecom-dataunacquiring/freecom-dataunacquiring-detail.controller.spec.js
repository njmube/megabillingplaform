'use strict';

describe('Controller Tests', function() {

    describe('Freecom_dataunacquiring Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_dataunacquiring, MockFreecom_acquiring_data;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_dataunacquiring = jasmine.createSpy('MockFreecom_dataunacquiring');
            MockFreecom_acquiring_data = jasmine.createSpy('MockFreecom_acquiring_data');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_dataunacquiring': MockFreecom_dataunacquiring,
                'Freecom_acquiring_data': MockFreecom_acquiring_data
            };
            createController = function() {
                $injector.get('$controller')("Freecom_dataunacquiringDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_dataunacquiringUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
