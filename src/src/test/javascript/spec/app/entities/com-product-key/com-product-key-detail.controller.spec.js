'use strict';

describe('Controller Tests', function() {

    describe('Com_product_key Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_product_key;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_product_key = jasmine.createSpy('MockCom_product_key');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_product_key': MockCom_product_key
            };
            createController = function() {
                $injector.get('$controller')("Com_product_keyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_product_keyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
