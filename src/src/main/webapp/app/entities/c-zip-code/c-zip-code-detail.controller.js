(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_zip_codeDetailController', C_zip_codeDetailController);

    C_zip_codeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_zip_code'];

    function C_zip_codeDetailController($scope, $rootScope, $stateParams, entity, C_zip_code) {
        var vm = this;
        vm.c_zip_code = entity;
        vm.load = function (id) {
            C_zip_code.get({id: id}, function(result) {
                vm.c_zip_code = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_zip_codeUpdate', function(event, result) {
            vm.c_zip_code = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
