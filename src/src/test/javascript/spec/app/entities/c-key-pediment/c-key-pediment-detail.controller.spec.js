'use strict';

describe('Controller Tests', function() {

    describe('C_key_pediment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_key_pediment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_key_pediment = jasmine.createSpy('MockC_key_pediment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_key_pediment': MockC_key_pediment
            };
            createController = function() {
                $injector.get('$controller')("C_key_pedimentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_key_pedimentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
