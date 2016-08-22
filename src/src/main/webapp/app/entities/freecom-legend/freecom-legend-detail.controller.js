(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_legendDetailController', Freecom_legendDetailController);

    Freecom_legendDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_legend', 'Freecom_taxlegends'];

    function Freecom_legendDetailController($scope, $rootScope, $stateParams, entity, Freecom_legend, Freecom_taxlegends) {
        var vm = this;

        vm.freecom_legend = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_legendUpdate', function(event, result) {
            vm.freecom_legend = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
