(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Part_conceptDetailController', Part_conceptDetailController);

    Part_conceptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Part_concept', 'Concept', 'Measure_unit'];

    function Part_conceptDetailController($scope, $rootScope, $stateParams, entity, Part_concept, Concept, Measure_unit) {
        var vm = this;

        vm.part_concept = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:part_conceptUpdate', function(event, result) {
            vm.part_concept = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
