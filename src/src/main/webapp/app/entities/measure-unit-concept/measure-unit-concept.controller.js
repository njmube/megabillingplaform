(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Measure_unit_conceptController', Measure_unit_conceptController);

    Measure_unit_conceptController.$inject = ['$scope', '$state', 'Measure_unit_concept'];

    function Measure_unit_conceptController ($scope, $state, Measure_unit_concept) {
        var vm = this;
        vm.measure_unit_concepts = [];
        vm.loadAll = function() {
            Measure_unit_concept.query(function(result) {
                vm.measure_unit_concepts = result;
            });
        };

        vm.loadAll();
        
    }
})();
