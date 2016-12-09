'use strict';

describe('Controller Tests', function() {

    describe('Com_public_notaries Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_public_notaries, MockCfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_public_notaries = jasmine.createSpy('MockCom_public_notaries');
            MockCfdi = jasmine.createSpy('MockCfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_public_notaries': MockCom_public_notaries,
                'Cfdi': MockCfdi
            };
            createController = function() {
                $injector.get('$controller')("Com_public_notariesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_public_notariesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
