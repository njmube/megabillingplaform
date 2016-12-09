'use strict';

describe('Controller Tests', function() {

    describe('Com_acquiring_data Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_acquiring_data, MockCom_public_notaries;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_acquiring_data = jasmine.createSpy('MockCom_acquiring_data');
            MockCom_public_notaries = jasmine.createSpy('MockCom_public_notaries');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_acquiring_data': MockCom_acquiring_data,
                'Com_public_notaries': MockCom_public_notaries
            };
            createController = function() {
                $injector.get('$controller')("Com_acquiring_dataDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_acquiring_dataUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
