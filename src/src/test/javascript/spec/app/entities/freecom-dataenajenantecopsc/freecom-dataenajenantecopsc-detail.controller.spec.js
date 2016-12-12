'use strict';

describe('Controller Tests', function() {

    describe('Freecom_dataenajenantecopsc Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_dataenajenantecopsc, MockFreecom_data_enajenante;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_dataenajenantecopsc = jasmine.createSpy('MockFreecom_dataenajenantecopsc');
            MockFreecom_data_enajenante = jasmine.createSpy('MockFreecom_data_enajenante');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_dataenajenantecopsc': MockFreecom_dataenajenantecopsc,
                'Freecom_data_enajenante': MockFreecom_data_enajenante
            };
            createController = function() {
                $injector.get('$controller')("Freecom_dataenajenantecopscDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_dataenajenantecopscUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
