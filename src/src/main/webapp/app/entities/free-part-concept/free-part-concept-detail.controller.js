(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_part_conceptDetailController', Free_part_conceptDetailController);

    Free_part_conceptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Free_part_concept', 'Free_concept', 'Measure_unit'];

    function Free_part_conceptDetailController($scope, $rootScope, $stateParams, entity, Free_part_concept, Free_concept, Measure_unit) {
        var vm = this;
        vm.free_part_concept = entity;
        vm.load = function (id) {
            Free_part_concept.get({id: id}, function(result) {
                vm.free_part_concept = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:free_part_conceptUpdate', function(event, result) {
            vm.free_part_concept = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
