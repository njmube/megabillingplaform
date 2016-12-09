'use strict';

describe('Controller Tests', function() {

    describe('Com_data_enajenante Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_data_enajenante, MockCom_public_notaries;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_data_enajenante = jasmine.createSpy('MockCom_data_enajenante');
            MockCom_public_notaries = jasmine.createSpy('MockCom_public_notaries');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_data_enajenante': MockCom_data_enajenante,
                'Com_public_notaries': MockCom_public_notaries
            };
            createController = function() {
                $injector.get('$controller')("Com_data_enajenanteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_data_enajenanteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
