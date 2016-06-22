(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_locationDetailController', C_locationDetailController);

    C_locationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_location', 'C_municipality', 'C_zip_code', 'C_colony'];

    function C_locationDetailController($scope, $rootScope, $stateParams, entity, C_location, C_municipality, C_zip_code, C_colony) {
        var vm = this;

        vm.c_location = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_locationUpdate', function(event, result) {
            vm.c_location = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
