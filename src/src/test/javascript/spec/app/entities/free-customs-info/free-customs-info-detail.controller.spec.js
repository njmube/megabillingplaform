'use strict';

describe('Controller Tests', function() {

    describe('Free_customs_info Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFree_customs_info, MockFree_concept, MockFree_part_concept;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFree_customs_info = jasmine.createSpy('MockFree_customs_info');
            MockFree_concept = jasmine.createSpy('MockFree_concept');
            MockFree_part_concept = jasmine.createSpy('MockFree_part_concept');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Free_customs_info': MockFree_customs_info,
                'Free_concept': MockFree_concept,
                'Free_part_concept': MockFree_part_concept
            };
            createController = function() {
                $injector.get('$controller')("Free_customs_infoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:free_customs_infoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
