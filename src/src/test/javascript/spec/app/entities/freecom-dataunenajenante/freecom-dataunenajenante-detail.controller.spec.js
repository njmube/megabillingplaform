'use strict';

describe('Controller Tests', function() {

    describe('Freecom_dataunenajenante Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_dataunenajenante, MockFreecom_data_enajenante;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_dataunenajenante = jasmine.createSpy('MockFreecom_dataunenajenante');
            MockFreecom_data_enajenante = jasmine.createSpy('MockFreecom_data_enajenante');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_dataunenajenante': MockFreecom_dataunenajenante,
                'Freecom_data_enajenante': MockFreecom_data_enajenante
            };
            createController = function() {
                $injector.get('$controller')("Freecom_dataunenajenanteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_dataunenajenanteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
