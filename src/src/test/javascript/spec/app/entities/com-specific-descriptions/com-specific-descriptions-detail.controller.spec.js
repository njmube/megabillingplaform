'use strict';

describe('Controller Tests', function() {

    describe('Com_specific_descriptions Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_specific_descriptions, MockCom_commodity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_specific_descriptions = jasmine.createSpy('MockCom_specific_descriptions');
            MockCom_commodity = jasmine.createSpy('MockCom_commodity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_specific_descriptions': MockCom_specific_descriptions,
                'Com_commodity': MockCom_commodity
            };
            createController = function() {
                $injector.get('$controller')("Com_specific_descriptionsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_specific_descriptionsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
