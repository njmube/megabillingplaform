'use strict';

describe('Controller Tests', function() {

    describe('Freecom_dataacquiringcopsc Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_dataacquiringcopsc, MockFreecom_acquiring_data;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_dataacquiringcopsc = jasmine.createSpy('MockFreecom_dataacquiringcopsc');
            MockFreecom_acquiring_data = jasmine.createSpy('MockFreecom_acquiring_data');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_dataacquiringcopsc': MockFreecom_dataacquiringcopsc,
                'Freecom_acquiring_data': MockFreecom_acquiring_data
            };
            createController = function() {
                $injector.get('$controller')("Freecom_dataacquiringcopscDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_dataacquiringcopscUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
