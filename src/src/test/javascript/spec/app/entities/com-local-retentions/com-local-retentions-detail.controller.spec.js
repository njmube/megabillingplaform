'use strict';

describe('Controller Tests', function() {

    describe('Com_local_retentions Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_local_retentions, MockCom_local_taxes;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_local_retentions = jasmine.createSpy('MockCom_local_retentions');
            MockCom_local_taxes = jasmine.createSpy('MockCom_local_taxes');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_local_retentions': MockCom_local_retentions,
                'Com_local_taxes': MockCom_local_taxes
            };
            createController = function() {
                $injector.get('$controller')("Com_local_retentionsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_local_retentionsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
