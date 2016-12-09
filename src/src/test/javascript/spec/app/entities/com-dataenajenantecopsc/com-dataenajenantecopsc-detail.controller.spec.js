'use strict';

describe('Controller Tests', function() {

    describe('Com_dataenajenantecopsc Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_dataenajenantecopsc, MockCom_data_enajenante;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_dataenajenantecopsc = jasmine.createSpy('MockCom_dataenajenantecopsc');
            MockCom_data_enajenante = jasmine.createSpy('MockCom_data_enajenante');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_dataenajenantecopsc': MockCom_dataenajenantecopsc,
                'Com_data_enajenante': MockCom_data_enajenante
            };
            createController = function() {
                $injector.get('$controller')("Com_dataenajenantecopscDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_dataenajenantecopscUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
