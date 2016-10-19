(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Measure_unit_conceptDetailController', Measure_unit_conceptDetailController);

    Measure_unit_conceptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Measure_unit_concept', 'Measure_unit', 'Taxpayer_concept'];

    function Measure_unit_conceptDetailController($scope, $rootScope, $stateParams, entity, Measure_unit_concept, Measure_unit, Taxpayer_concept) {
        var vm = this;
        vm.measure_unit_concept = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:measure_unit_conceptUpdate', function(event, result) {
            vm.measure_unit_concept = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
