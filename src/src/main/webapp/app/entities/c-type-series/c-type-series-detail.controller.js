(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_type_seriesDetailController', C_type_seriesDetailController);

    C_type_seriesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_type_series'];

    function C_type_seriesDetailController($scope, $rootScope, $stateParams, entity, C_type_series) {
        var vm = this;

        vm.c_type_series = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_type_seriesUpdate', function(event, result) {
            vm.c_type_series = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
