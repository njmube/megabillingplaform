'use strict';

describe('Controller Tests', function() {

    describe('Com_dataacquiringcopsc Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_dataacquiringcopsc, MockCom_acquiring_data;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_dataacquiringcopsc = jasmine.createSpy('MockCom_dataacquiringcopsc');
            MockCom_acquiring_data = jasmine.createSpy('MockCom_acquiring_data');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_dataacquiringcopsc': MockCom_dataacquiringcopsc,
                'Com_acquiring_data': MockCom_acquiring_data
            };
            createController = function() {
                $injector.get('$controller')("Com_dataacquiringcopscDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_dataacquiringcopscUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
