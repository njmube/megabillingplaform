'use strict';

describe('Controller Tests', function() {

    describe('Freecom_specific_descriptions Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_specific_descriptions;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_specific_descriptions = jasmine.createSpy('MockFreecom_specific_descriptions');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_specific_descriptions': MockFreecom_specific_descriptions
            };
            createController = function() {
                $injector.get('$controller')("Freecom_specific_descriptionsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_specific_descriptionsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
