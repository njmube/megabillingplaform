'use strict';

describe('Controller Tests', function() {

    describe('Com_donees Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_donees, MockCfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_donees = jasmine.createSpy('MockCom_donees');
            MockCfdi = jasmine.createSpy('MockCfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_donees': MockCom_donees,
                'Cfdi': MockCfdi
            };
            createController = function() {
                $injector.get('$controller')("Com_doneesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_doneesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
