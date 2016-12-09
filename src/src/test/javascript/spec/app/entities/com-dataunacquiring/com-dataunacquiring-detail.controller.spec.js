'use strict';

describe('Controller Tests', function() {

    describe('Com_dataunacquiring Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_dataunacquiring, MockCom_acquiring_data;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_dataunacquiring = jasmine.createSpy('MockCom_dataunacquiring');
            MockCom_acquiring_data = jasmine.createSpy('MockCom_acquiring_data');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_dataunacquiring': MockCom_dataunacquiring,
                'Com_acquiring_data': MockCom_acquiring_data
            };
            createController = function() {
                $injector.get('$controller')("Com_dataunacquiringDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_dataunacquiringUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
