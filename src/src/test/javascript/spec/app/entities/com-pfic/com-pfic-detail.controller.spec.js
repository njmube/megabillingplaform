'use strict';

describe('Controller Tests', function() {

    describe('Com_pfic Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_pfic, MockCfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_pfic = jasmine.createSpy('MockCom_pfic');
            MockCfdi = jasmine.createSpy('MockCfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_pfic': MockCom_pfic,
                'Cfdi': MockCfdi
            };
            createController = function() {
                $injector.get('$controller')("Com_pficDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_pficUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
